package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

/**
 * @author Yoshimasa Tanabe
 */
case class Task(id: Long, label: String)

object Task {
  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM task").as(task *)
  }

  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("INSERT INTO task (label) VALUES ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Long): Unit = {
    DB.withConnection { implicit c =>
      SQL("DELETE FROM task WHERE id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

  val task = {
    get[Long]("id") ~
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

}
