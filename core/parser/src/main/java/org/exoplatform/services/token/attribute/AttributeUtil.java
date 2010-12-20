/**
 * Copyright (C) 2009 eXo Platform SAS.
 * 
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.services.token.attribute;

import org.exoplatform.services.chars.ValueVerifier;
import org.exoplatform.services.html.HTMLNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by eXoPlatform Studio
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * May 8, 2006
 */
public class AttributeUtil
{

   public synchronized List<String> getAttributes(HTMLNode node, Map<String, String> map)
   {
      return getAttributes(node, null, map, null);
   }

   public synchronized List<String> getAttributes(HTMLNode node, Map<String, String> map, ValueVerifier verifier)
   {
      return getAttributes(node, null, map, verifier);
   }

   public synchronized String getAttribute(HTMLNode node, Map<String, String> map, ValueVerifier verifier)
   {
      Attribute attr = getAttribute(node, map);
      if (attr != null)
      {
         if (verifier == null || verifier.verify(attr.getValue()))
            return attr.getValue();
      }
      List<HTMLNode> children = node.getChildrenNode();
      for (HTMLNode ele : children)
      {
         String link = getAttribute(ele, map, verifier);
         if (link != null)
            return link;
      }
      return null;
   }

   public synchronized List<String> getAttributes(HTMLNode node, List<String> list, Map<String, String> map,
      ValueVerifier verifier)
   {
      if (list == null)
         list = new ArrayList<String>();
      Attribute attr = getAttribute(node, map);
      if (attr != null)
      {
         if (verifier == null || verifier.verify(attr.getValue()))
            list.add(attr.getValue());
      }

      List<HTMLNode> children = node.getChildrenNode();
      for (HTMLNode ele : children)
         getAttributes(ele, list, map, verifier);
      return list;
   }

   public synchronized Attribute getAttribute(HTMLNode node, Map<String, String> map)
   {
      Set<String> keys = map.keySet();
      Iterator<String> iter = keys.iterator();
      while (iter.hasNext())
      {
         String key = iter.next();
         if (node.isNode(key))
         {
            Attributes attrs = AttributeParser.getAttributes(node);
            int idx = attrs.indexOf(map.get(key));
            if (idx > -1)
               return attrs.get(idx);
         }
      }
      return null;
   }

}

