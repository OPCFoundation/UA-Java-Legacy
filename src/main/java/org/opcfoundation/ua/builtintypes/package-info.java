/**
OPC UA Part 6 defines 25 builtin types. Those 25 types are represented with the following java classes.
Most builtin classes are immutable.
<table summary="builtin types table">
<tr><th>ID</th><th>Name</th><th>Java Class</th><th>Notes</th></tr>
<tr><td>1</td><td>Boolean</td><td>java.lang.Boolean</td></tr>
<tr><td>2</td><td>SByte</td><td>java.lang.Byte</td></tr>
<tr><td>3</td><td>Byte</td><td>org.opcfoundation.ua.builtintypes.UnsignedByte</td></tr>
<tr><td>4</td><td>Int16</td><td>java.lang.Short</td></tr>
<tr><td>5</td><td>UInt16</td><td>org.opcfoundation.ua.builtintypes.UnsignedShort</td></tr>
<tr><td>6</td><td>Int32</td><td>java.lang.Integer</td></tr>
<tr><td>7</td><td>UInt32</td><td>org.opcfoundation.ua.builtintypes.UnsignedInteger</td></tr>
<tr><td>8</td><td>Int64</td><td>java.lang.Long</td></tr>
<tr><td>9</td><td>UInt64</td><td>org.opcfoundation.ua.builtintypes.UnsignedLong</td></tr>
<tr><td>10</td><td>Float</td><td>java.lang.Float</td></tr>
<tr><td>11</td><td>Double</td><td>java.lang.Double</td></tr>
<tr><td>12</td><td>String</td><td>java.lang.String</td></tr>
<tr><td>13</td><td>DateTime</td><td>org.opcfoundation.ua.builtintypes.DateTime</td></tr>
<tr><td>14</td><td>Guid</td><td>java.util.UUID</td></tr>
<tr><td>15</td><td>ByteString</td><td>org.opcfoundation.ua.builtintypes.ByteString</td></tr>
<tr><td>16</td><td>XmlElement</td><td>org.opcfoundation.ua.builtintypes.XmlElement</td></tr>
<tr><td>17</td><td>NodeId</td><td>org.opcfoundation.ua.builtintypes.NodeId</td></tr>
<tr><td>18</td><td>ExpandedNodeId</td><td>org.opcfoundation.ua.builtintypes.ExpandedNodeId</td></tr>
<tr><td>19</td><td>StatusCode</td><td>org.opcfoundation.ua.builtintypes.StatusCode</td></tr>
<tr><td>20</td><td>QualifiedName</td><td>org.opcfoundation.ua.builtintypes.QualifiedName</td></tr>
<tr><td>21</td><td>LocalizedText</td><td>org.opcfoundation.ua.builtintypes.LocalizedText</td></tr>
<tr><td>22</td><td>ExtensionObject</td><td>org.opcfoundation.ua.builtintypes.ExtensionObject</td></tr>
<tr><td>23</td><td>DataValue</td><td>org.opcfoundation.ua.builtintypes.DataValue</td></tr>
<tr><td>24</td><td>Variant</td><td>org.opcfoundation.ua.builtintypes.Variant</td></tr>
<tr><td>25</td><td>DiagnosticInfo</td><td>org.opcfoundation.ua.builtintypes.DiagnosticInfo</td></tr>
</table>

<br>Also, see class org.opcfoundation.ua.encoding.utils.BuiltinsMap for builtins id, uri and java class mappings.
 */
package org.opcfoundation.ua.builtintypes;

