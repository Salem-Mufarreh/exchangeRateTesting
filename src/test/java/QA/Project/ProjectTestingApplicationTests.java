package QA.Project;

import QA.Project.Entity.CurrencyEntity;
import QA.Project.Service.CurrencyService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjectTestingApplicationTests {

	@Autowired
	private CurrencyService _currencyService;
	@Test
	void contextLoads() {
		List<CurrencyEntity> result = _currencyService.getAllRates();
		Assertions.assertTrue(result != null);
	}


}
