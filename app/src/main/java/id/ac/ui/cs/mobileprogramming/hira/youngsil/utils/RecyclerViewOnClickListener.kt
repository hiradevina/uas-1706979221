package id.ac.ui.cs.mobileprogramming.hira.youngsil.utils

import id.ac.ui.cs.mobileprogramming.hira.youngsil.entity.Todo

interface RecyclerViewOnItemSelected {
    fun onItemSelected(todo: Todo)
}

interface RecyclerViewOnItemDone {
    fun onItemDone(todo: Todo)
}