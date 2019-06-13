/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.zgkxzx.modbus4And.msg;

import com.zgkxzx.modbus4And.base.ModbusUtils;
import com.zgkxzx.modbus4And.code.FunctionCode;
import com.zgkxzx.modbus4And.exception.ModbusTransportException;
import com.zgkxzx.modbus4And.sero.util.queue.ByteQueue;

public class WriteCoilResponse extends ModbusResponse {
    private int writeOffset;
    private boolean writeValue;

    @Override
    public byte getFunctionCode() {
        return FunctionCode.WRITE_COIL;
    }

    WriteCoilResponse(int slaveId) throws ModbusTransportException {
        super(slaveId);
    }

    WriteCoilResponse(int slaveId, int writeOffset, boolean writeValue) throws ModbusTransportException {
        super(slaveId);
        this.writeOffset = writeOffset;
        this.writeValue = writeValue;
    }

    @Override
    protected void writeResponse(ByteQueue queue) {
        ModbusUtils.pushShort(queue, writeOffset);
        ModbusUtils.pushShort(queue, writeValue ? 0xff00 : 0);
    }

    @Override
    protected void readResponse(ByteQueue queue) {
        writeOffset = ModbusUtils.popUnsignedShort(queue);
        writeValue = ModbusUtils.popUnsignedShort(queue) == 0xff00;
    }

    public int getWriteOffset() {
        return writeOffset;
    }

    public boolean isWriteValue() {
        return writeValue;
    }
}
