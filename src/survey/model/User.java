package survey.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import survey.model.security.Right;
import survey.model.security.Role;

/**
 * �û���
 */
@Entity
public class User extends BaseEntity{
	@Id
	@GeneratedValue
	private Integer id;
	private String email;
	private String password;
	private String nickName;
	//ע��ʱ��
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date regDate;
	
	//Ȩ���ܺ�
	private long[] rightSum;
	//�Ƿ��ǳ�������Ա
	private boolean superAdmin;

	//��ɫ����
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns =	{@JoinColumn(name = "user_id") },
		inverseJoinColumns = { @JoinColumn(name= "role_id")}	)		//�м������
	private Set<Role> roles = new HashSet<Role>();
	
	public Integer getId() {
		return id;
	}
	
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	/*
	 * �ж��û��Ƿ����Ȩ��
	 */
	public boolean hasRight(Right r){
		if(rightSum == null){
			return false;
		}
		else{
			int pos = r.getRightPos();
			long code = r.getRightCode();
			return !((rightSum[pos] & code) == 0 );
		}
	}
}