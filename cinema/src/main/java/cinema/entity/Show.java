package cinema.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long showId;
		
		private String showName;
		private String showTime;
		private String showGenre;
		
		@EqualsAndHashCode.Exclude
		@ToString.Exclude
		@ManyToMany(mappedBy = "shows", cascade = CascadeType.PERSIST)
		private Set<Cinema> cinema = new HashSet<>();
}
