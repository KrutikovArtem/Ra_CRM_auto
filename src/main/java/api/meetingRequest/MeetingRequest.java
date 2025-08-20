package api.meetingRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {
    private FilterRequest filter;
    private int page;
    private int size;
    private List<SortOption> sortBy;
}
