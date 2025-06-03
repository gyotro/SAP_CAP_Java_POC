package com.sap.gyo.handlers

import com.sap.cds.ql.Insert
import com.sap.cds.ql.Upsert
import com.sap.cds.ql.cqn.CqnUpsert
import com.sap.cds.services.cds.CdsReadEventContext
import com.sap.cds.services.cds.CqnService
import com.sap.cds.services.handler.EventHandler
import com.sap.cds.services.handler.annotations.Before
import com.sap.cds.services.handler.annotations.On
import com.sap.cds.services.handler.annotations.ServiceName
import com.sap.cds.services.persistence.PersistenceService
import com.sap.gyo.services.ConcurUserService
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*


@Component
@ServiceName("AdminUser")
open class AdminUserHandler(
    private val db: PersistenceService,
    private val concurUserService: ConcurUserService,
) : EventHandler {

    companion object {
        private val log = LoggerFactory.getLogger(AdminUserHandler::class.java)
    }

    //@Transactional
    @Before(event = [CqnService.EVENT_READ], entity = ["AdminUser.User"])
    fun beforeReadUsers(ctx: CdsReadEventContext) {
        log.info("Executing beforeReadUsers...")
        val concurUsers =  runBlocking {
        concurUserService.getUsers()
        }
        val upsertedUsers = mutableListOf<HashMap<String, Any?>>()

        concurUsers.Resources.forEach { concurUser ->
            val user: HashMap<String, Any?> = hashMapOf(
                "userId" to UUID.randomUUID().toString(),
                "name" to concurUser.name?.givenName,
                "lastName" to concurUser.name?.familyName,
                "empID" to concurUser.enterpriseExtension?.employeeNumber,
                "loginId" to concurUser.userName,
                "approver" to ""
            )
            upsertedUsers.add(user)
        }

        val upsert: CqnUpsert = Upsert.into("com.sap.gyo.User").entries(upsertedUsers)
        val result = db.run { upsert }
        log.info("Upsert result: $result")

        }
    }

