/**
 * 
 */
package top.anets.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import top.anets.service.SapService;

/**
 * @author Administrator
 *
 */
@Component
public class Sap {
	@Autowired
	private SapService sapService;
	
	@Scheduled(initialDelay=5555, fixedRate=6666)
    public void fetchInvoices() {
//    	1从sap取发票
		sapService.revoke("2000", "1","1");
    }
}
