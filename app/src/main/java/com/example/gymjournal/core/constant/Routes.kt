package com.example.gymjournal.core.constant

object Routes {

    const val  SPLASH = "Splash"
    const val START_WORKOUT = "StartWorkout"
    const val HOME = "Home"
    const val PROFILE = "Profile"
    const val PROFILE_SETTING = "ProfileSetting"
    const val SETTINGS = "Settings"
    const val ONBOARDING = "onBoarding"
    const val CHANGE_PASSWORD = "change_password"

    //GOAL
    const val GOAL_SCREEN = "Goals"
    const val EDIT_GOAL_SCREEN = "edit_goal/{goalId}"
    const val ADD_GOAL_SCREEN = "AddGoal"

    fun editGoal(goalId: Int): String = "edit_goal/$goalId"

    // Moves / Exercise Routes
    const val MOVES = "moves"
    const val ADD_MOVE = "add_move"
    const val DETAIL_MOVE = "detail_move/{id}"

    fun detailMove(id: Int) = "detail_move/$id"


    //ROUTTINE
    const val ROUTINE = "routines"
    const val EDIT_EXERCISE = "edit_exercise"
    const val SELECT_MOVE = "select_move"
    const val ROUTINE_DETAIL = "routine_detail"

    fun addExercise(dayId: Int, exerciseId: Int) = "add_routine_exercise/$dayId/$exerciseId"
    fun routineDetail(routineId: Int) = "$ROUTINE_DETAIL/$routineId"
    fun editExercise(exerciseId: Int) = "$EDIT_EXERCISE/$exerciseId"
    fun selectMove(dayId: Int) = "$SELECT_MOVE/$dayId"

    //AUTH
    const val  AUTH = "Auth"
    const val START_SCREEN = "Start"
    const val LOGIN = "Login"
    const val REGISTER = "Register"

}
