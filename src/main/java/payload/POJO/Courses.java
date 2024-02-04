package payload.POJO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Courses {
	List<WebAutomation> webAutomation;
	List<API> Api;
	List<Mobile> Mobile;
}
