package com.xenatronics.webagenda.common.util

sealed class NodeSort(val nodeType:NodeType){
    class Text(nodeType:NodeType):NodeSort(nodeType=nodeType)
    class Date(nodeType: NodeType):NodeSort(nodeType = nodeType)
}
