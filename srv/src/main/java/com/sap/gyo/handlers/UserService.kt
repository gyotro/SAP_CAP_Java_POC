package com.sap.gyo.handlers

import com.sap.cds.ql.CQL
import com.sap.cds.reflect.CdsService
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
    private val concurUserService: ConcurUserService
) : EventHandler {

    private val log = LoggerFactory.getLogger(AdminUserHandler::class.java)

    @Before(event = [CqnService.EVENT_READ], entity = ["AdminUser.User"])
    fun beforeReadUsers(ctx: CdsReadEventContext) {
        log.info("Executing beforeReadUsers...")
        val concurUsers = runBlocking { concurUserService.getUsers() }

        val upsertedUsers = concurUsers.map {
            User.create().apply {
                userId = UUID.fromString(it.id)
                name = it.name.givenName
                lastName = it.name.familyName
                empID = it.extension.employeeNumber
                approver = "000001" // dummy or extracted value
            }
        }

        db.run { CQL.upsert(upsertedUsers) }
    }
}
