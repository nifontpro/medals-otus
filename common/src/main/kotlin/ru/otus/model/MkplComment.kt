package ru.otus.model

data class MkplComment(
    var id: MkplCommentId = MkplCommentId.NONE,
    var entityId: MkplEntityId = MkplEntityId.NONE,
    var entityType: MkplEntityType = MkplEntityType.NONE,
    var comment: String = "",
    var rating: Int = -1,
    val permissionsClient: MutableSet<MkplCommentPermissionClient> = mutableSetOf()
)
