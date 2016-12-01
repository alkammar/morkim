package lib.morkim.mfw.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import lib.morkim.mfw.mvp.PendingViewableUpdate;


@SupportedAnnotationTypes("lib.morkim.mfw.mvp.PendingViewableUpdate")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PendingEventProcessor extends AbstractProcessor {

	private static final String PACKAGE_PATH = "lib.morkim.mfw.generated.update.listeners";
	private static final String CLASS_SUFFIX = "Pending";

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


		// for each javax.lang.model.element.Element annotated with the CustomAnnotation
		for (Element element : roundEnv.getElementsAnnotatedWith(PendingViewableUpdate.class)) {

			String interfaceName = element.getSimpleName().toString();

			StringBuilder builder = new StringBuilder()
					.append("package " + PACKAGE_PATH + ";\n\n")
					.append("import lib.morkim.mfw.ui.AbstractUpdateListenerPending;\n")
					.append("import lib.morkim.mfw.ui.Controller;\n\n")
					.append("public class ").append(interfaceName).append(CLASS_SUFFIX).append("\n")
					.append("\textends AbstractUpdateListenerPending<").append(element.toString()).append(">\n")
					.append("\timplements ").append(element.toString()).append(" {\n\n");

//			try {
//				Class interfaceClass = Class.forName(element.asType().toString());
//				builder.append(interfaceClass.getName());
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//				builder.append(e);
//			}

//			TypeMirror typeMirror = element.();

//			for (asfd : annotations.addAll())
			final TypeElement typeElement = (TypeElement) element;

			for (TypeMirror parent : typeElement.getInterfaces()) {

				DeclaredType kind = (DeclaredType) parent;
				Element o = kind.asElement();

				for (Element method : o.getEnclosedElements())
					addMethod(builder, method);

//				for (method : parent.)
			}


			for (Element child : element.getEnclosedElements()) {

				addMethod(builder, child);
			}


			builder.append("}\n"); // close class


			writeJavaFile(interfaceName + CLASS_SUFFIX, builder);
		}


		return true;
	}

	private void addMethod(StringBuilder builder, Element child) {
		if (child.getKind() == ElementKind.METHOD) {
			String methodName = child.getSimpleName().toString();

			builder.append("\t@Override\n")
					.append("\tpublic void ")
					.append(methodName).append("(");

			String[] params = new String[0];

			if (!child.toString().contains("()"))
				params = child.toString().substring(methodName.length() + 1, child.toString().length() - 1)
						.split(",");

			generateMethodSignature(builder, params);

			builder.append(") {\n");

			builder.append("\t\tpendingEventsExecutor.add(new Controller.PendingEvent() {\n")
				.append("\t\t\t@Override\n")
				.append("\t\t\tpublic void onExecuteWhenUiAvailable() {\n");

			builder.append("\t\t\t\t")
					.append("updateListener.").append(methodName).append("(");

			for (int i = 0; i < params.length; i++) {
				builder.append("var").append(i + 1);
				if (i < params.length - 1) builder.append(", ");
			}

			builder.append(");\n");

			builder.append("\t\t\t}\n")
					.append("\t\t});");

			builder.append("\n")
					.append("\t}\n\n");
		}
	}

	private void generateMethodSignature(StringBuilder builder, String[] params) {
		for (int i = 0; i < params.length; i++) {
			builder.append("final ").append(params[i]).append(" ").append("var").append(i + 1);
			if (i < params.length - 1) builder.append(", ");
		}
	}

	private void writeJavaFile(String className, StringBuilder builder) {
		try { // write the file
			JavaFileObject source = processingEnv.getFiler().createSourceFile(PACKAGE_PATH + "." + className);


			Writer writer = source.openWriter();
			writer.write(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// Note: calling e.printStackTrace() will print IO errors
			// that occur from the file already existing after its first run, this is normal
		}
	}
}
