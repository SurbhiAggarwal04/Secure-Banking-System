package com.sbs.internetbanking.utilities;

import com.sbs.internetbanking.enums.RequestStatus;
import com.sbs.internetbanking.enums.RequestType;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.model.Request;
import com.sbs.internetbanking.model.User;

public class RequestGenerator {
	public static Request newAccountApprovalRequest(User user) {
		Request newAccountApprovalRequest = new Request();
		newAccountApprovalRequest.setRequestFromAccountNum(user.getUsername());
		newAccountApprovalRequest.setRequestId(IdentifierGenerator.getRequestId());
		newAccountApprovalRequest.setRequestType(RequestType.CREATE_ACCOUNT.name());
		newAccountApprovalRequest.setRequestApprovedBy("ANONYMOUS");
		newAccountApprovalRequest.setRequestContent("Approve new request");
		newAccountApprovalRequest.setRequestFor(Roles.ROLE_MANAGER.name());
		newAccountApprovalRequest.setRequestStatus(RequestStatus.PENDING_APPROVAL.name());
		// newAccountApprovalRequest.setCreationTimeStamp();
		return newAccountApprovalRequest;
	}
}