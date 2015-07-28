package  {
	import com.netease.protobuf.*;
	import com.netease.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class LoginResult extends com.netease.protobuf.Message {
		public static const SUCCEED:FieldDescriptor$TYPE_BOOL = new FieldDescriptor$TYPE_BOOL("LoginResult.succeed", "succeed", (1 << 3) | com.netease.protobuf.WireType.VARINT);

		public var succeed:Boolean;

		public static const ERRORCODE:FieldDescriptor$TYPE_INT32 = new FieldDescriptor$TYPE_INT32("LoginResult.errorCode", "errorCode", (2 << 3) | com.netease.protobuf.WireType.VARINT);

		private var errorCode$field:int;

		private var hasField$0:uint = 0;

		public function removeErrorCode():void {
			hasField$0 &= 0xfffffffe;
			errorCode$field = new int();
		}

		public function get hasErrorCode():Boolean {
			return (hasField$0 & 0x1) != 0;
		}

		public function set errorCode(value:int):void {
			hasField$0 |= 0x1;
			errorCode$field = value;
		}

		public function get errorCode():int {
			return errorCode$field;
		}

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.netease.protobuf.WritingBuffer):void {
			com.netease.protobuf.WriteUtils.writeTag(output, com.netease.protobuf.WireType.VARINT, 1);
			com.netease.protobuf.WriteUtils.write$TYPE_BOOL(output, this.succeed);
			if (hasErrorCode) {
				com.netease.protobuf.WriteUtils.writeTag(output, com.netease.protobuf.WireType.VARINT, 2);
				com.netease.protobuf.WriteUtils.write$TYPE_INT32(output, errorCode$field);
			}
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var succeed$count:uint = 0;
			var errorCode$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.netease.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (succeed$count != 0) {
						throw new flash.errors.IOError('Bad data format: LoginResult.succeed cannot be set twice.');
					}
					++succeed$count;
					this.succeed = com.netease.protobuf.ReadUtils.read$TYPE_BOOL(input);
					break;
				case 2:
					if (errorCode$count != 0) {
						throw new flash.errors.IOError('Bad data format: LoginResult.errorCode cannot be set twice.');
					}
					++errorCode$count;
					this.errorCode = com.netease.protobuf.ReadUtils.read$TYPE_INT32(input);
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}
