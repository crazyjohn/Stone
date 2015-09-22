package sample.cluster.transformation;

import java.io.Serializable;

//#messages
public interface JobMessages {

	@SuppressWarnings("serial")
	public static class Job implements Serializable {
		private final String text;

		public Job(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}

	@SuppressWarnings("serial")
	public static class JobResult implements Serializable {
		private final String text;

		public JobResult(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		@Override
		public String toString() {
			return "TransformationResult(" + text + ")";
		}
	}

	@SuppressWarnings("serial")
	public static class JobFailed implements Serializable {
		private final String reason;
		private final Job job;

		public JobFailed(String reason, Job job) {
			this.reason = reason;
			this.job = job;
		}

		public String getReason() {
			return reason;
		}

		public Job getJob() {
			return job;
		}

		@Override
		public String toString() {
			return "JobFailed(" + reason + ")";
		}
	}

	public static final String BACKEND_REGISTRATION = "BackendRegistration";

}
// #messages