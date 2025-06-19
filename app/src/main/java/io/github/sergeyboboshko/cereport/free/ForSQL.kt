package io.github.sergeyboboshko.cereport.free

import androidx.compose.runtime.Composable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.sergeyboboshko.composeentity.daemons.BaseEntity
import io.github.sergeyboboshko.composeentity.daemons.IconVector
import io.github.sergeyboboshko.composeentity_ksp.base.CeFree
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@CeFree ()
@ObjectGeneratorCE(type = GeneratorType.Free, generationLevel = GenerationLevel.VIEW_MODEL)
data class ForSQL(
    override var id: Long
):BaseEntity {
    @Composable
    override fun StatusIcon() {
        TODO("Not yet implemented")
    }

    @Composable
    override fun StatusVectosIcon(): IconVector {
        TODO("Not yet implemented")
    }
}