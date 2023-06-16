package com.jeft.composelearn.ui.theme

import androidx.compose.animation.core.MutableTransitionState

const val TAG = "ComposeLean"

enum class AnimState {
    VISIBLE, INVISIBLE, APPEARING, DISAPPEARING
}

fun MutableTransitionState<Boolean>.getAnimationState(): AnimState {
    return when {
        this.isIdle && this.currentState -> AnimState.VISIBLE
        this.isIdle && !this.currentState -> AnimState.INVISIBLE
        !this.isIdle && this.currentState -> AnimState.DISAPPEARING
        else -> AnimState.APPEARING
    }
}