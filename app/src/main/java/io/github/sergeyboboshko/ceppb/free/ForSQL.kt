package io.github.sergeyboboshko.ceppb.free

import androidx.compose.runtime.Composable
import io.github.sergeyboboshko.composeentity.daemons.BaseEntity
import io.github.sergeyboboshko.composeentity.daemons.IconVector
import io.github.sergeyboboshko.composeentity_ksp.base.CeFree
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.CeGenerator
import io.github.sergeyboboshko.composeentity_ksp.entity.GenerationLevel

@CeFree ()
@CeGenerator(type = GeneratorType.Free, generationLevel = GenerationLevel.VIEW_MODEL)
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