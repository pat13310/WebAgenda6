package com.xenatronics.webagenda.common.util

sealed class NodeType{
    object Ascending:NodeType()
    object Descending:NodeType()
}
