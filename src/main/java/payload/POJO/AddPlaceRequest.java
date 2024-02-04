package payload.POJO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPlaceRequest {	
	Location location;
	int accuracy;
	String name;
	String phone_number;
	String address;
	List<String> types;
	String website;
	String language;
}
