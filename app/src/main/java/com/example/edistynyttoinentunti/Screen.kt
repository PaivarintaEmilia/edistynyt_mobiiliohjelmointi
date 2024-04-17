package com.example.edistynyttoinentunti

sealed class Screen(val route: String) {

    data object Login: Screen("loginScreen")
    data object Categories: Screen("categoriesScreen")

    data object PostsScreen: Screen("postsScreen")
    data object EditCategoryScreen: Screen("editCategoryScreen/{categoryId}")
    data object RegisterScreen: Screen("registerScreen")

}