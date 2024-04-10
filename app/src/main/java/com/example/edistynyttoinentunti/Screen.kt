package com.example.edistynyttoinentunti

sealed class Screen(val route: String) {

    object Login: Screen("loginScreen")
    object Categories: Screen("categoriesScreen")

    object PostsScreen: Screen("postsScreen")
    object EditCategoryScreen: Screen("editCategoryScreen")

}