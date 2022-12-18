package nl.tudelft.sem.template.user.domain.entities;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;import nl.tudelft.sem.template.user.domain.NetId;import nl.tudelft.sem.template.user.domain.Position;
import javax.persistence.*;

@Table(name = "message")
@Entity
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String receiver;

	@Column
	private String sender;

	@Column
	private long activityId;

	@Column
	String content;

	@Column
	@Enumerated(EnumType.STRING)
	Position position;

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
