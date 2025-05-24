package com.sap.gyo.handlers

import com.sap.cds.services.cds.CdsCreateEventContext
import com.sap.cds.services.cds.CdsReadEventContext
import com.sap.cds.services.cds.CqnService
import com.sap.cds.services.handler.EventHandler
import com.sap.cds.services.handler.annotations.On
import com.sap.cds.services.handler.annotations.ServiceName
import org.springframework.stereotype.Component
import java.util.function.Consumer

//@Component
//@ServiceName("AdminService")
open class AdminService : EventHandler {
    private val products: MutableMap<Any?, MutableMap<String?, Any?>?> = HashMap()

    @On(event = [CqnService.EVENT_CREATE], entity = ["AdminService.Products"])
    fun onCreate(context: CdsCreateEventContext) {
        context.cqn.entries().forEach(Consumer { e: MutableMap<String?, Any?>? -> products.put(e!!["ID"], e) })
        context.setResult(context.cqn.entries())
    }

    @On(event = [CqnService.EVENT_READ], entity = ["AdminService.Products"])
    fun onRead(context: CdsReadEventContext) {
        context.setResult(products.values)
    }
}