package com.haks.haksvn.transfer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.haks.haksvn.common.code.model.Code;

@Entity
@Table(name="transfer_source")
public class TransferSource {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "transfer_source_seq",unique = true, nullable = false)
	private int transferSourceSeq;
	
	@Column(name = "path", nullable = false, length=500)
	private String path;
	
	@Column(name = "dest_path", nullable = true, length=500)
	private String destPath;
	
	@Column(name = "revision", nullable = false)
	private long revision;
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn(name="transfer_seq")
	private Transfer transfer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="transfer_source_type", referencedColumnName="code_id")
	private Code transferSourceTypeCode;
	
	@Transient
	private boolean isLocked;
	
	@Transient
	private boolean deleted;
	
	public TransferSource(){
		
	}

	public int getTransferSourceSeq() {
		return transferSourceSeq;
	}

	public void setTransferSourceSeq(int transferSourceSeq) {
		this.transferSourceSeq = transferSourceSeq;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getDestPath(){
		return destPath;
	}
	
	public void setDestPath(String destPath){
		this.destPath = destPath;
	}

	public long getRevision() {
		return revision;
	}

	public void setRevision(long revision) {
		this.revision = revision;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
	
	public Code getTransferSourceTypeCode(){
		return transferSourceTypeCode;
	}
	
	public void setTransferSourceTypeCode(Code code){
		transferSourceTypeCode = code;
	}
	
	public boolean getIsLocked(){
		return isLocked;
	}
	
	public void setIsLocked(boolean isLocked){
		this.isLocked = isLocked;
	}
	
	public boolean getDeleted(){
		return deleted;
	}
	
	public void setDeleted(boolean deleted){
		this.deleted = deleted;
	}

	public static class Builder{
		
		private TransferSource transferSource;
		
		private Builder(TransferSource transferSource){
			this.transferSource = transferSource;
		}
		
		public static Builder getBuilder(){
			return getBuilder(new TransferSource());
		}
		
		public static Builder getBuilder(TransferSource transferSource){
			return new Builder(transferSource);
		}
		
		public TransferSource build(){
			return transferSource;
		}
		
		public Builder transferSourceSeq(int transferSourceSeq){
			transferSource.setTransferSourceSeq(transferSourceSeq);
			return this;
		}
		
		public Builder path(String path){
			transferSource.setPath(path);
			return this;
		}
		
		public Builder destPath(String destPath){
			transferSource.setDestPath(destPath);
			return this;
		}
		
		public Builder revision(long revision){
			transferSource.setRevision(revision);
			return this;
		}
		
		public Builder transfer(Transfer transfer){
			transferSource.setTransfer(transfer);
			return this;
		}
		
		public Builder transferSourceTypeCode(Code code){
			transferSource.setTransferSourceTypeCode(code);
			return this;
		}
		
		public Builder isLocked(boolean isLocked){
			transferSource.setIsLocked(isLocked);
			return this;
		}
		
		public Builder deleted(boolean deleted){
			transferSource.setDeleted(deleted);
			return this;
		}
		
	} 
}
