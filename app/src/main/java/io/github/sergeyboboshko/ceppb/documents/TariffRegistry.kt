package io.github.sergeyboboshko.ceppb.documents

import io.github.sergeyboboshko.composeentity_ksp.base.CeEntity

import io.github.sergeyboboshko.ceppb.details.DetailsTariffRegistry
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity_ksp.base.CeCreateTable
import io.github.sergeyboboshko.composeentity_ksp.base.CeField
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType

@CeEntity(tableName = "doc_tariff_registry")
@CeCreateTable("doc_tariff_registry")
@CeGenerator(type = GeneratorType.Document,
    label = "@@doc_tariff_registry",
    hasDetails = true,
    detailsEntityClass = DetailsTariffRegistry::class)
data class DocTariffRegistry(
    
    override var id: Long,
    @CeField(label = "@@date_label", placeHolder = "@@date_placeholder",
        type = FieldTypeHelper.DATE_TIME, useForOrder = true,)
    override var date: Long,
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean ,
    @CeField(label = "@@describe_label", placeHolder = "@@describe_placeholder",type= FieldTypeHelper.TEXT)
    var describe:String
): CommonDocumentEntity(
    id,date, number, isPosted , isMarkedForDeletion
)