package com.sap.gyo.handlers

import com.sap.cds.ql.Upsert
import com.sap.cds.services.cds.CdsReadEventContext
import com.sap.cds.services.cds.CqnService
import com.sap.cds.services.handler.EventHandler
import com.sap.cds.services.handler.annotations.Before
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

    private val log = LoggerFactory.getLogger(AdminUserHandler::class.java)

    @Before(event = [CqnService.EVENT_READ], entity = ["AdminUser.User"])
    fun beforeReadUsers(ctx: CdsReadEventContext) {
        log.info("Executing beforeReadUsers...")
        val concurUsers = runBlocking { concurUserService.getUsers() }

        val upsertedUsers = mutableListOf<HashMap<String, Any>>()

        concurUsers.forEach { concurUser ->
            val user: HashMap<String, Any> = hashMapOf(
                "userId" to concurUser.id,
                "name" to concurUser.name.givenName,
                "lastName" to concurUser.name.familyName,
                "empID" to concurUser.enterpriseExtension.employeeNumber,
                "loginId" to concurUser.userName,
                "approver" to ""
            )
            upsertedUsers.add(user)
        }

        val upsert = Upsert.into("AdminUser.User").entries(upsertedUsers)
        val result = db.run { upsert }


    }
}
