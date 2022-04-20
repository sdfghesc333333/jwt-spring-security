package com.springbootproject.springbootproject.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
@Table(name = "t_permission")
public class PermissionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "permission_key")
	@JsonProperty(value = "permission_key")
	private String permissionKey;

	@Column(name = "permission_name")
	@JsonProperty(value = "permission_name")
	private String permissionName;

	@Column(name = "state")
	private int state;

	@Column(name = "created_at")
	@JsonProperty(value = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	@JsonProperty(value = "updated_at")
	private Date updatedAt;
	
	@Column(name = "deleted_at")
	@JsonProperty(value = "deleted_at")
	private Date deletedAt;
}
