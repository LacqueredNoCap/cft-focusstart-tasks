package lacquered.task6.protocol.message;

public class ResponseMessage extends Message {
    private final RequestMessage requestMessage;
    private final ResponseStatusCode responseStatusCode;

    public ResponseMessage(String content,
                           RequestMessage requestMessage,
                           ResponseStatusCode responseStatusCode) {
        super(content, MessageType.SERVER_RESPONSE);
        this.requestMessage = requestMessage;
        this.responseStatusCode = responseStatusCode;
    }

    public ResponseMessage(RequestMessage requestMessage, ResponseStatusCode responseStatusCode) {
        this(responseStatusCode.name(), requestMessage, responseStatusCode);
    }

    public RequestMessage getRequestMessage() {
        return requestMessage;
    }

    public ResponseStatusCode getResponseStatusCode() {
        return responseStatusCode;
    }
}
