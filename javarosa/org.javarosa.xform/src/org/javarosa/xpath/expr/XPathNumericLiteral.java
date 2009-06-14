package org.javarosa.xpath.expr;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.javarosa.core.model.IFormDataModel;
import org.javarosa.core.model.condition.EvaluationContext;
import org.javarosa.core.util.externalizable.DeserializationException;
import org.javarosa.core.util.externalizable.ExtUtil;
import org.javarosa.core.util.externalizable.PrototypeFactory;

public class XPathNumericLiteral extends XPathExpression {
	public double d;

	public XPathNumericLiteral () { } //for deserialization

	public XPathNumericLiteral (Double d) {
		this.d = d.doubleValue();
	}
	
	public Object eval (IFormDataModel model, EvaluationContext evalContext) {
		return new Double(d);
	}

	public String toString () {
		return "{num:" + Double.toString(d) + "}";
	}
		
	public boolean equals (Object o) {
		if (o instanceof XPathNumericLiteral) {
			XPathNumericLiteral x = (XPathNumericLiteral)o;
			return (Double.isNaN(d) ? Double.isNaN(x.d) : d == x.d);
		} else {
			return false;
		}
	}
	
	public void readExternal(DataInputStream in, PrototypeFactory pf) throws IOException, DeserializationException {
		if (in.readByte() == (byte)0x00) {
			d = ExtUtil.readNumeric(in);
		} else {
			d = ExtUtil.readDecimal(in);
		}
	}

	public void writeExternal(DataOutputStream out) throws IOException {
		if (d == (int)d) {
			out.writeByte(0x00);
			ExtUtil.writeNumeric(out, (int)d);
		} else {
			out.writeByte(0x01);
			ExtUtil.writeDecimal(out, d);
		}
	}
}
