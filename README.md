# mobile-sale-apache-beam

Apache Beam is an open source, unified model for defining both batch and streaming data-parallel processing pipelines. Using one of the open source Beam SDKs, you build a program that defines the pipeline. 

There are five main conceptions in Beam: 
1. Pipeline
2. PCollection
3. PTransform
4. ParDO
5. DoFn

Pipeline: A Pipeline encapsulates the workflow of your entire data processing tasks from start to finish. This includes reading input data, transforming that data, and writing output data. All Beam driver programs must create a Pipeline. When you create the Pipeline, you must also specify the execution options that tell the Pipeline where and how to run.

PCollection: A PCollection represents a distributed data set that your Beam pipeline operates on. The data set can be bounded, meaning it comes from a fixed source like a file, or unbounded, meaning it comes from a continuously updating source via a subscription or other mechanism

PTransform: A PTransform represents a data processing operation, or a step, in your pipeline. Every PTransform takes one or more PCollection objects as input, performs a processing function that you provide on the elements of that PCollection, and produces zero or more output PCollection objects.

ParDo: ParDo is a Beam transform for generic parallel processing. The ParDo processing paradigm is similar to the “Map” phase of a Map/Shuffle/Reduce-style algorithm: a ParDo transform considers each element in the input PCollection, performs some processing function (your user code) on that element, and emits zero, one, or multiple elements to an output PCollection.

DoFn: A DoFn applies your logic in each element in the input PCollection and lets you populate the elements of an output PCollection. To be included in your pipeline, it’s wrapped in a ParDo PTransform.

### How to Develop Building and Running a Mobile Sale Data Processing Job Using Apache Beam

#### Just download this project and import as Maven project in your IDE and run it, debug it locally easily. 

For example, if our input CSV file contains the following mobile data:

1,Nokia,Lumia 635,6<br/>
2,Motorola,Moto E5 Plus,2<br/>
1,Nokia,808 PureView,3<br/>
3,Redmi,Note 7 Pro,5<br/>
3,Redmi,Note 6 Pro,3<br/>
3,Redmi,Note 6 Pro,3<br/>
1,Nokia,8 Sirocco,12<br/>
2,Motorola,Moto G6,2<br/>

Then the final sold mobile result will be like this:

Redmi,11<br/>
Nokia,21<br/>
Motorola,4<br/>

#### Many way to run this project:
1. Junit
2. Right click through main java file.
3. mvn compile exec:java -Dexec.mainClass=com.jai.MobileSale -Dexec.args="--output=counts" -Pdirect-runner

