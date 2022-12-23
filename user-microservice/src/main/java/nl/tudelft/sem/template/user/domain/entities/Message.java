package nl.tudelft.sem.template.user.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.template.user.domain.Position;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;
import java.util.Objects;

@Table(name = "message")
@Entity
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @JsonProperty("receiver")
    private String receiver;
    @Column
    @JsonProperty("sender")
    private String sender;
    @Column
    @JsonProperty("activityId")
    private long activityId;
    @Column
    @JsonProperty("content")
    String content;
    @Column
    @Enumerated(EnumType.STRING)
    @JsonProperty("position")
    Position position;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return  getReceiver().equals(message.getReceiver())
            && sender.equals(message.sender)
            && getContent().equals(message.getContent()) && position == message.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReceiver(), sender, getActivityId(), getContent(), position);
    }

    /**
     * A basic constructor for a message.
     *
     * @param receiver   NetId of the message recipient
     * @param sender     NetId of the message sender
     * @param activityId activity Id of the activity the message is concerned with
     * @param content    the message in String format
     * @param position   the position that the user applied for
     */
    public Message(String receiver, String sender, long activityId, String content, Position position) {
        this.receiver = receiver;
        this.sender = sender;
        this.activityId = activityId;
        this.content = content;
        this.position = position;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
