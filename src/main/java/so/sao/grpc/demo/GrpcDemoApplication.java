package so.sao.grpc.demo;

import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import so.sao.test.protobuf.GreeterGrpc;
import so.sao.test.protobuf.Hello;
import so.sao.test.protobuf.PhoneServiceGrpc;
import so.sao.test.protobuf.TestServer;

@SpringBootApplication
public class GrpcDemoApplication {

	@GrpcService(PhoneServiceGrpc.class)
	public class GrpcServerService extends PhoneServiceGrpc.PhoneServiceImplBase {
		@Override
		public void addPhoneToUser(TestServer.AddPhoneToUserRequest request, StreamObserver<TestServer.AddPhoneToUserResponse> responseObserver) {
			TestServer.AddPhoneToUserResponse response = null;
			if(request.getPhoneNumber().length() == 11 ){
				System.out.printf("uid = %s , phone type is %s, nubmer is %s\n", request.getUid(), request.getPhoneType(), request.getPhoneNumber());
				response = TestServer.AddPhoneToUserResponse.newBuilder().setResult(true).build();
			}else{
				System.out.printf("The phone nubmer %s is wrong!\n",request.getPhoneNumber());
				response = TestServer.AddPhoneToUserResponse.newBuilder().setResult(false).build();
			}
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}
	}

	@GrpcService(GreeterGrpc.class)
	public class GrpcHelloService extends GreeterGrpc.GreeterImplBase{
		@Override
		public void sayHello(Hello.HelloRequest request, StreamObserver<Hello.HelloResponse> responseObserver) {
			Hello.HelloResponse response = Hello.HelloResponse.newBuilder().setMessage("Hello =============> " + request.getName()).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}
	}


	public static void main(String[] args) {

		SpringApplication.run(GrpcDemoApplication.class, args);
	}
}
