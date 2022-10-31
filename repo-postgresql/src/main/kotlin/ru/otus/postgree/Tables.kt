package ru.otus.postgree

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import ru.otus.model.*
import java.util.*

object CommentsTable : StringIdTable("Comments") {

	val comment = varchar("comment", 128)
	val rating = integer("rating")

	val entityId = reference("entity_id", EntityTable.id).index()
	val entityType = enumeration("entity_type", MkplEntityType::class).index()
	val lock = varchar("lock", 50)

	override val primaryKey = PrimaryKey(id)

	// Mapper functions from sql-like table to MkplComment
	fun from(res: InsertStatement<Number>) = MkplComment(
		id = MkplCommentId(res[id].toString()),
		comment = res[comment],
		rating = res[rating],
		entityId = MkplEntityId(res[entityId].toString()),
		entityType = res[entityType],
		lock = MkplLock(res[lock])
	)

	fun from(res: ResultRow) = MkplComment(
		id = MkplCommentId(res[id].toString()),
		comment = res[comment],
		rating = res[rating],
		entityId = MkplEntityId(res[entityId].toString()),
		entityType = res[entityType],
		lock = MkplLock(res[lock])
	)
}

object EntityTable : StringIdTable("Entity") {
	override val primaryKey = PrimaryKey(CommentsTable.id)
}

open class StringIdTable(name: String = "", columnName: String = "id", columnLength: Int = 50) : IdTable<String>(name) {
	override val id: Column<EntityID<String>> =
		varchar(columnName, columnLength).uniqueIndex().default(generateUuidAsStringFixedSize())
			.entityId()
	override val primaryKey by lazy { super.primaryKey ?: PrimaryKey(id) }
}

fun generateUuidAsStringFixedSize() = UUID.randomUUID().toString().replace("-", "").substring(0, 9)
