package cinema.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Cinema {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cinemaId;
	
	private String cinemaName;
	private String cinemaAddress;
	private String cinemaCity;
	private String cinemaState;
	private String cinemaZip;
	private String cinemaPhone;
	
//	set customer
//	Join Column table customer
//	add many to many relationship
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "cinema_customer", 
	joinColumns = @JoinColumn (name = "cinema_id"),
	inverseJoinColumns =  @JoinColumn (name = "customer_id"))
	
	private Set<Customer> customers = new HashSet<>();
	
	
//	Join Column table 
//	set employees 
//	add many to many relationship
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
	
//	Join Column table
//	set shows
//	many to many relationship
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "cinema_show", 
	joinColumns = @JoinColumn (name = "cinema_id"), 
	inverseJoinColumns = @JoinColumn (name = "show_id"))
			
	private Set<Show> shows = new HashSet<>();
	
}
