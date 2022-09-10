package ru.otus.stubs

import ru.otus.model.*

object CommentStubEx1 {

	val COMMENT_AD_1: MkplComment
		get() = MkplComment(
			id = MkplCommentId("CA1"),
			entityId = MkplEntityId("EA111"),
			comment = "Отличный товар для дома",
			rating = 5,
			entityType = MkplEntityType.AD,
			permissionsClient = mutableSetOf(
				MkplCommentPermissionClient.READ,
				MkplCommentPermissionClient.UPDATE,
				MkplCommentPermissionClient.DELETE,
				MkplCommentPermissionClient.MAKE_VISIBLE_PUBLIC,
				MkplCommentPermissionClient.MAKE_VISIBLE_GROUP,
				MkplCommentPermissionClient.MAKE_VISIBLE_OWNER,
			)
		)

	val COMMENT_USER_1: MkplComment
		get() = MkplComment(
			id = MkplCommentId("CU1"),
			entityId = MkplEntityId("EU1"),
			comment = "Хороший, добрый пользователь",
			rating = 5,
			entityType = MkplEntityType.USER,
			permissionsClient = mutableSetOf(
				MkplCommentPermissionClient.READ,
				MkplCommentPermissionClient.UPDATE,
				MkplCommentPermissionClient.DELETE,
				MkplCommentPermissionClient.MAKE_VISIBLE_PUBLIC,
				MkplCommentPermissionClient.MAKE_VISIBLE_GROUP,
				MkplCommentPermissionClient.MAKE_VISIBLE_OWNER,
			)
		)
}