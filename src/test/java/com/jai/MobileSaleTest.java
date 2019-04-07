package com.jai;

import com.jai.MobileSale;
import com.jai.MobileSale.MobileSaleOptions;
import org.apache.beam.sdk.testing.TestPipeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MobileSaleTest {
	@Test
	public void testDebuggingWordCount() throws Exception {

		MobileSaleOptions options = TestPipeline.testingPipelineOptions().as(MobileSaleOptions.class);
		options.setInputFile("mobile_sale_list.csv");
		options.setOutput("mobile_sold_list.csv");
		MobileSale.executeMobileSale(options);
	}
}
