	package org.omsf.store.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer storeNo;
	private Integer noticeNo;
}
