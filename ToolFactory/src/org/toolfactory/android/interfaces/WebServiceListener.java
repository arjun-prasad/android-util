package org.toolfactory.android.interfaces;

/**
 * Listener used while calling web services
 * 
 * @param S Type of response when service call succeed, passed as argument in onSuccess() method
 * @param E Type of response when service call failed, passed as argument in onError() method
 * @param C Type of response when service call canceled, passed as argument in onCancel() method
 * @param P Type of response when required to notify some progress, passed as argument in onProgress() method
 * 
 * @author Name : <b>Arjun</b><br>Date : 16 June 2016
 * 
 */
public interface WebServiceListener<S,E,C,P> {
	void onSuccess(S s);
	void onError(E e);
	void onCancel(C c);
	void onProgress(P p);
}
