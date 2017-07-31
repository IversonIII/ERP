package cn.iverson.erp.biz.exception;

@SuppressWarnings("serial")
public class ErpException extends RuntimeException {
	public ErpException (String message) {
		super(message);
	}
}
