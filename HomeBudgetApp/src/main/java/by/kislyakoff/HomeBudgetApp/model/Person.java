package by.kislyakoff.HomeBudgetApp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import by.kislyakoff.HomeBudgetApp.model.dict.Role;

@Entity
@Table(name = "person")
public class Person {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 64, message = "Имя должно быть от 2 до 64 символов длиной")
    @Column(name = "username")
    private String username;
	
	@Column(name = "password")
	@NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 4, message = "Пароль должен содержать не менее 4 символов")
    private String password;
	
	@Column(name = "role")
	@NotNull(message = "Укажите тип")
	private Role role;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false) 
	// updatable = false because by default when save() updated person createdAt will set null
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name= "active")
	@NotNull(message = "Укажите тип")
	private Boolean isActive;

	public Person() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", isActive=" + isActive + "]";
	}
	
	
	
	

}
