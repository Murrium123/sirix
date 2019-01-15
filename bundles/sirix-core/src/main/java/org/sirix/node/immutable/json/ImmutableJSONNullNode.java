package org.sirix.node.immutable.json;

import static com.google.common.base.Preconditions.checkNotNull;
import org.sirix.node.Kind;
import org.sirix.node.interfaces.StructNode;
import org.sirix.node.json.JSONNullNode;
import org.sirix.node.xdm.ElementNode;

/**
 * Immutable element wrapper.
 *
 * @author Johannes Lichtenberger
 *
 */
public final class ImmutableJSONNullNode extends AbstractImmutableJSONStructuralNode {

  /** Mutable {@link JSONNullNode}. */
  private final JSONNullNode mNode;

  /**
   * Private constructor.
   *
   * @param node mutable {@link ElementNode}
   */
  private ImmutableJSONNullNode(final JSONNullNode node) {
    mNode = checkNotNull(node);
  }

  /**
   * Get an immutable JSON-array node instance.
   *
   * @param node the mutable {@link ImmutableJSONNullNode} to wrap
   * @return immutable JSON-array node instance
   */
  public static ImmutableJSONNullNode of(final JSONNullNode node) {
    return new ImmutableJSONNullNode(node);
  }

  @Override
  public StructNode structDelegate() {
    return mNode;
  }

  @Override
  public Kind getKind() {
    return Kind.JSON_NULL;
  }
}