package com.almousleck.response;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FeedBackResponse {
    private Double note;
    private String comment;
    private boolean ownFeedback;
}
