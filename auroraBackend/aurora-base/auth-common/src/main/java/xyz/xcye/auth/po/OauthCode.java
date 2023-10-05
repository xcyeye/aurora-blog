package xyz.xcye.auth.po;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xcye <br/>
 * @table oauth_code <br/>
 * @description TODO <br/>
 * @date 2022-12-14 23:53:19 <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "oauth_code数据表的实体类")
public class OauthCode implements Serializable {

    private static final long serialVersionUID = 13247652346523L;

    /**
     *
     */
    @Schema(title = "")
    private String code;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "")
    private Long authentication;

}