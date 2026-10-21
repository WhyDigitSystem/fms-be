package com.whydigit.fms.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.fms.dto.CreatedUpdatedDate;
import com.whydigit.fms.dto.Role;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
public class UserVO {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersgen")
	@SequenceGenerator(name = "usersgen", sequenceName = "usersseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "userid")
	private Long id;

	@Column(name = "username",length =150)
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "employeename",length =150)
	private String employeeName;
	@Column(name = "nickname",length =150)
	private String nickName;
	@Column(name = "email",length =25)
	private String email;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "mobileno",length =25)
	private String mobileNo;
	@Column(name = "usertype",length =25)
	private String userType;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchcode;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "loginstatus")
	private boolean loginStatus;
	@Column(name = "isActive")
	private boolean active;
	@Column(name = "createdby",length =25)
	private String createdby;
	@Column(name = "modifiedby",length =25)
	private String updatedby;
	@Column(name = "role")
	private Role role;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<UserLoginRolesVO> roleAccessVO;

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<UserLoginClientAccessVO> clientAccessVO;

	@OneToMany(mappedBy = "userVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<UserLoginBranchAccessibleVO> branchAccessibleVO;
	
	private Date accountRemovedDate;
	
	@ManyToOne
	@JoinColumn(name="companyid")
	private CompanyVO companyVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
