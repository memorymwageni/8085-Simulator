package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class SUBMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'a':
			opcode[0] = (byte) 0x97;
			break;
		case 'm':
			opcode[0] = (byte) 0x96;
			break;
		case 'b':
			opcode[0] = (byte) 0x90;
			break;
		case 'c':
			opcode[0] = (byte) 0x91;
			break;
		case 'd':
			opcode[0] = (byte) 0x92;
			break;
		case 'e':
			opcode[0] = (byte) 0x93;
			break;
		case 'h':
			opcode[0] = (byte) 0x94;
			break;
		case 'l':
			opcode[0] = (byte) 0x95;
			break;
		default:
			throw new IllegalArgumentException("Argument " + arguments[0]
					+ " wird nicht unterstuetzt!");
		}

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		byte b = 0;

		int clock = 4;
		switch (opcode) {
		case (byte) 0x97:
			b = Processor.getInstance().getRegisterA();
			break;
		case (byte) 0x90:
			b = Processor.getInstance().getRegisterB();
			break;
		case (byte) 0x91:
			b = Processor.getInstance().getRegisterC();
			break;
		case (byte) 0x92:
			b = (Processor.getInstance().getRegisterD());
			break;
		case (byte) 0x93:
			b = (Processor.getInstance().getRegisterE());
			break;
		case (byte) 0x94:
			b = (Processor.getInstance().getRegisterH());
			break;
		case (byte) 0x95:
			b = (Processor.getInstance().getRegisterL());
			break;
		case (byte) 0x96:
			b = Memory.getInstance().get(
					Processor.getInstance().getRegisterHL());
			clock = 7;
			break;
		default:
			break;
		}

		short a2 = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a2 - b2;
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);

		return clock;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode >= (byte) 0x90) && (opcode <= (byte) 0x97);
	}

	@Override
	public byte size() {
		return 1;
	}

	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 1 && Simulator.isRegistername(args[0]);
	}
}
