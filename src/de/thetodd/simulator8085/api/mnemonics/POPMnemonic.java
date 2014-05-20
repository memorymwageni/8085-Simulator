package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class POPMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length != 1) {
			throw new IllegalArgumentException("Falsche Anzahl Argumente!");
		}
		String arg = arguments[0].toLowerCase();
		if (arg.equals("b")) {
			opcode[0] = (byte) 0xC1;
		} else if (arg.equals("d")) {
			opcode[0] = (byte) 0xD1;
		} else if (arg.equals("h")) {
			opcode[0] = (byte) 0xE1;
		} else if (arg.equals("psw")) {
			opcode[0] = (byte) 0xF1;
		}

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		switch (opcode) {
		case (byte) 0xC1:
			Processor.getInstance().setRegisterC(Memory.getInstance().popStack());
			Processor.getInstance().setRegisterB(Memory.getInstance().popStack());
			break;
		case (byte) 0xD1:
			Processor.getInstance().setRegisterE(Memory.getInstance().popStack());
			Processor.getInstance().setRegisterD(Memory.getInstance().popStack());
			break;
		case (byte) 0xE1:
			Processor.getInstance().setRegisterL(Memory.getInstance().popStack());
			Processor.getInstance().setRegisterH(Memory.getInstance().popStack());
			break;
		case (byte) 0xF1:
			Processor.getInstance().setRegisterF(Memory.getInstance().popStack());
			Processor.getInstance().setRegisterA(Memory.getInstance().popStack());
			break;
		default:
			break;
		}
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xC1) || (opcode == (byte) 0xD1)
				|| (opcode == (byte) 0xE1) || (opcode == (byte) 0xF1);
	}

	@Override
	public byte size() {
		return 1;
	}
}