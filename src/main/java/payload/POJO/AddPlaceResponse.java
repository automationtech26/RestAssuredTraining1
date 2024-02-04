package payload.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPlaceResponse {

	String status;
	String place_id;
	String scope;
	String reference;
	String id;
}
