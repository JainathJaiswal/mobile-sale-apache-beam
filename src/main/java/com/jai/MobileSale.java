package com.jai;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

public class MobileSale {

	public interface MobileSaleOptions extends PipelineOptions {
		@Description("Path of the file to read from")
		@Default.String("mobile_sale_list.csv")
		@Required
		String getInputFile();

		void setInputFile(String value);

		@Description("Path of the file to write to")
		@Default.String("mobile_sold_list.csv")
		@Required
		String getOutput();

		void setOutput(String value);
	}

	public static class ExtractMobileDoFn extends DoFn<String, KV<String, Long>> {

		private static final long serialVersionUID = 1416080832985153752L;

		@ProcessElement
		public void processElement(@Element String element, OutputReceiver<KV<String, Long>> receiver) {

			String[] split = element.split(",");
			if (split.length < 4) {
				receiver.output(null);
			}

			String key = split[1];
			Long value = Long.valueOf(split[3]);
			receiver.output(KV.of(key, value));

		}
	}

	public static class FormatMobileSaleDoFn extends DoFn<KV<String, Iterable<Long>>, String> {

		private static final long serialVersionUID = 3898421917157746286L;

		@ProcessElement
		public void processElement(ProcessContext context) {
			long total = 0;
			String mobileBrand = context.element().getKey();
			Iterable<Long> sells = context.element().getValue();
			for (Long amount : sells) {
				total += amount;
			}
			context.output(mobileBrand + "," + total);
		}
	}

	static void executeMobileSale(MobileSaleOptions options) {
		Pipeline p = Pipeline.create(options);

		PCollection<String> input = p.apply("ReadFromCSV", TextIO.read().from(options.getInputFile()));

		PCollection<KV<String, Long>> readMobileSaleFromCSVAndConvertToKV = input.apply("ConvertToKV",
				ParDo.of(new ExtractMobileDoFn()));

		PCollection<KV<String, Iterable<Long>>> kvpCollection = readMobileSaleFromCSVAndConvertToKV.apply("KeyValues",
				GroupByKey.<String, Long>create());

		PCollection<String> sumUpValuesByKey = kvpCollection.apply("SumUpValuesByKey",
				ParDo.of(new FormatMobileSaleDoFn()));

		sumUpValuesByKey.apply("WriteToCSV", TextIO.write().to(options.getOutput()).withoutSharding());

		p.run().waitUntilFinish();
	}

	public static void main(String args[]) throws Exception {
		MobileSaleOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MobileSaleOptions.class);
		MobileSale.executeMobileSale(options);
	}
}