/**
 * 
 */
package top.anets.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import top.anets.entity.Business;
import top.anets.entity.Path;

/**
 * @author Administrator
 *
 */
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "business")
@PropertySource("classpath:/settings.properties")
public class BusinessVo  extends Business{
    private List<Path> paths;
    private String remainNum;
    private String undistributedNum;
    private String sumNum;
    @NotNull
    private Integer speed = 0;
    
    
    
    private Double wsSum;
    private Double seSum;
//    @NotNull
//    private Integer limit =500;
    
    
    
	public Integer getSpeed() {
		return speed;
	}

	public Double getWsSum() {
		return wsSum;
	}

	public void setWsSum(Double wsSum) {
		this.wsSum = wsSum;
	}

	public Double getSeSum() {
		return seSum;
	}

	public void setSeSum(Double seSum) {
		this.seSum = seSum;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
 

	public String getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(String remainNum) {
		this.remainNum = remainNum;
	}

	public String getUndistributedNum() {
		return undistributedNum;
	}

	public void setUndistributedNum(String undistributedNum) {
		this.undistributedNum = undistributedNum;
	}

	public String getSumNum() {
		return sumNum;
	}

	public void setSumNum(String sumNum) {
		this.sumNum = sumNum;
	}

	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	} 
    
}
