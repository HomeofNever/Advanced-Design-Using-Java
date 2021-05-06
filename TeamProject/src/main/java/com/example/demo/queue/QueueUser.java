package com.example.demo.queue;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * The single user in the queue
 */
@Entity
@Table(name = "queue_user")
public class QueueUser implements Serializable {
    /**
     * All possible status of the QueueUser object
     */
    public enum QUEUE_USER_STATUS {WAITING, IN_PROGRESS, FINISHED, LEFT};

    /**
     * QueueUser id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * User property of the QueueUser
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    /**
     * The assistant user who is helping with the current user
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="assistant_id")
    private User assistant;

    /**
     * The id of the current queue the user is joined in
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="queue_id")
    private Queue queue;

    /**
     * The status of the QueueUser
     */
    @Column(nullable = false, columnDefinition = "varchar(15) default 'WAITING'")
    private String status;

    /**
     * The note/message left by the current QueueUser
     */
    @Column(nullable = true)
    private String note;

    /**
     * The timestamp when the user joined the queue
     */
    @Column(name = "join_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp joinedAt;

    /**
     * The timestamp when the user left the queue
     */
    @Column(name = "ended_at")
    private Timestamp endedAt;

    /**
     * @return the user id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id set the input id as the user id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return get the User property of the QueueUser object
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the status of the QueueUser object
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the assistant user who is helping with the current user
     */
    public User getAssistant() {
        return assistant;
    }

    /**
     * @return the entire queue
     */
    public Queue getQueue() {
        return queue;
    }

    /**
     * @return the note left by the current user
     */
    public String getNote() {
        return note;
    }

    /**
     * @return the timestamp when the current queue user joined in the queue
     */
    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    /**
     * @return the timestamp when the current queue user left the queue
     */
    public Timestamp getEndedAt() {
        return endedAt;
    }

    /**
     * @param status set the input status (in String) as the new status of the queue user
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param status set the input status (in QUEUE_USER_STATUS) as the new status of the queue user
     */
    public void setStatus(QUEUE_USER_STATUS status) {
        this.status = status.name();
    }

    /**
     * @param user change the user property of the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param assistant set the assistant user who is helping with the current user to the given one
     */
    public void setAssistant(User assistant) {
        this.assistant = assistant;
    }

    /**
     * @param queue the queue the current user is belonging to
     */
    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    /**
     * @param endedAt the timestamp when the queer user left the queue
     */
    public void setEndedAt(Timestamp endedAt) {
        this.endedAt = endedAt;
    }

    /**
     * @param note the note to be set
     */
    public void setNote(String note) {
        this.note = note;
    }
}
