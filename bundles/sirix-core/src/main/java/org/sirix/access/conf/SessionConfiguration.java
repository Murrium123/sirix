/**
 * Copyright (c) 2011, University of Konstanz, Distributed Systems Group
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the University of Konstanz nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sirix.access.conf;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Objects;

import java.security.Key;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.sirix.access.DatabaseImpl;
import org.sirix.access.SessionImpl;
import org.sirix.api.NodeReadTrx;
import org.sirix.api.NodeWriteTrx;

/**
 * <h1>SessionConfiguration</h1>
 * 
 * <p>
 * Holds the {@link SessionImpl}-wide settings that can not change within the
 * runtime of a {@link SessionImpl}. This included stuff like commit-threshold
 * and number of usable write/read transactions. Each
 * {@link SessionConfiguration} is only bound through the location to a
 * {@link DatabaseImpl} and related resources.
 * </p>
 */
public final class SessionConfiguration {

	// STATIC STANDARD FIELDS

	/** Number of concurrent exclusive write transactions. */
	public static final int MAX_WRITE_TRANSACTIONS = 1;

	/** Number of concurrent read transactions. */
	public static final int MAX_READ_TRANSACTIONS = 512;

	/** Commit threshold. */
	public static final int COMMIT_THRESHOLD = 262144;

	/** Default User. */
	public static final String DEFAULT_USER = "ALL";
	// END STATIC STANDARD FIELDS

	// MEMBERS FOR FLEXIBLE FIELDS
	/** Numbers of allowed {@link NodeWriteTrx} instances. */
	public final int mWtxAllowed;

	/** Numbers of allowed {@link NodeReadTrx} instances. */
	public final int mRtxAllowed;

	/** Number of node modifications until an automatic commit occurs. */
	public final int mCommitThreshold;

	/** User for this session. */
	public final String mUser;
	// END MEMBERS FOR FIXED FIELDS

	/** ResourceConfiguration for this ResourceConfig. */
	private final String mResource;

	/**
	 * Convenience constructor using the standard settings.
	 * 
	 * @param builder
	 *          {@link Builder} reference
	 */
	private SessionConfiguration(
			final @Nonnull SessionConfiguration.Builder builder) {
		mWtxAllowed = builder.mWtxAllowed;
		mRtxAllowed = builder.mRtxAllowed;
		mCommitThreshold = builder.mCommitThreshold;
		mUser = builder.mUser;
		mResource = builder.mResource;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(mWtxAllowed, mRtxAllowed, mCommitThreshold, mUser,
				mResource);
	}

	@Override
	public final boolean equals(final @Nullable Object obj) {
		if (obj instanceof SessionConfiguration) {
			final SessionConfiguration other = (SessionConfiguration) obj;
			return Objects.equal(mWtxAllowed, other.mWtxAllowed)
					&& Objects.equal(mRtxAllowed, other.mRtxAllowed)
					&& Objects.equal(mCommitThreshold, other.mCommitThreshold)
					&& Objects.equal(mUser, other.mUser)
					&& Objects.equal(mResource, other.mResource);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("Resource", mResource)
				.add("RtxAllowed", mRtxAllowed).add("WtxAllowed", mWtxAllowed)
				.add("Commit threshold", mCommitThreshold).add("User", mUser)
				.toString();
	}

	/**
	 * Getter for the file.
	 * 
	 * @return the file for the configuration
	 */
	public String getResource() {
		return mResource;
	}

	/**
	 * Builder class for generating new {@link SessionConfiguration} instance.
	 */
	public static final class Builder {

		/** Numbers of allowed IWriteTransaction Instances. */
		private int mWtxAllowed = SessionConfiguration.MAX_WRITE_TRANSACTIONS;

		/** Numbers of allowed IWriteTransaction Instances. */
		private int mRtxAllowed = SessionConfiguration.MAX_READ_TRANSACTIONS;

		/** Number of node modifications until an automatic commit occurs. */
		private int mCommitThreshold = SessionConfiguration.COMMIT_THRESHOLD;

		/** User for this session. */
		private String mUser = SessionConfiguration.DEFAULT_USER;

		/** Resource for the this session. */
		private final String mResource;

		/**
		 * Constructor for the {@link Builder} with fixed fields to be set.
		 * 
		 * @param res
		 *          to be set.
		 */
		public Builder(@Nonnull final String res) {
			mResource = checkNotNull(res);
		}

		/**
		 * Setter for field mWtxAllowed.
		 * 
		 * @param wtxAllowed
		 *          new value for field
		 * @return reference to the builder object
		 */
		public Builder setWtxAllowed(@Nonnegative final int wtxAllowed) {
			checkArgument(wtxAllowed < 1, "Value must be > 0!");
			mWtxAllowed = wtxAllowed;
			return this;
		}

		/**
		 * Setter for field mRtxAllowed.
		 * 
		 * @param paramRtxAllowed
		 *          new value for field
		 * @return reference to the builder object
		 */
		public Builder setRtxAllowed(@Nonnegative final int pRtxAllowed) {
			checkArgument(pRtxAllowed < 1, "Value must be > 0!");
			mRtxAllowed = pRtxAllowed;
			return this;
		}

		/**
		 * Setter for field mCommitThreshold.
		 * 
		 * @param commitThreshold
		 *          new value for field
		 * @return reference to the builder object
		 */
		public Builder setCommitThreshold(@Nonnegative final int commitThreshold) {
			checkArgument(commitThreshold < 100, "Value must be > 100!");
			mCommitThreshold = commitThreshold;
			return this;
		}

		/**
		 * Setter for field mUser.
		 * 
		 * @param user
		 *          new value for field
		 * @return reference to the builder object
		 */
		public Builder setUser(@Nonnull final String user) {
			mUser = checkNotNull(user);
			return this;
		}

		/**
		 * Set key for cipher.
		 * 
		 * @param key
		 *          key for cipher
		 * @return reference to the builder object
		 */
		public Builder setKey(final @Nonnull Key key) {
			return this;
		}

		/**
		 * Building a new {@link SessionConfiguration} with immutable fields.
		 * 
		 * @return a new {@link SessionConfiguration}.
		 */
		public SessionConfiguration build() {
			return new SessionConfiguration(this);
		}
	}
}
